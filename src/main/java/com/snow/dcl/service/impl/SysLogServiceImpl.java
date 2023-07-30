package com.snow.dcl.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.snow.dcl.annotation.SysOperateLog;
import com.snow.dcl.dao.SysLogRepository;
import com.snow.dcl.model.SysLog;
import com.snow.dcl.model.SysRole;
import com.snow.dcl.service.SysLogService;
import com.snow.dcl.utils.SysLogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysLogServiceImpl
 * (功能描述)
 * 操作日志接口实现
 * @Author Dcl_Snow
 * @Create 2023/7/19 10:56
 * @Version 1.0.0
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogRepository sysLogRepository;

    @Override
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog sysLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysOperateLog sysOperateLog = method.getAnnotation(SysOperateLog.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        // 描述
        sysLog.setDescription(sysOperateLog.value());

        sysLog.setRequestIp(ip);
        sysLog.setAddress(SysLogUtils.getCityInfo(sysLog.getRequestIp()));
        sysLog.setMethod(methodName);
        sysLog.setUsername(username);
        sysLog.setParams(getParameter(method, joinPoint.getArgs()));
        // 记录登录用户，隐藏密码信息
        if (signature.getName().equals("login") && StringUtils.hasText(sysLog.getParams())) {
            JSONObject obj = JSONUtil.parseObj(sysLog.getParams());
            sysLog.setUsername(obj.getStr("username", ""));
            sysLog.setParams(JSONUtil.toJsonStr(Dict.create().set("username", sysLog.getUsername())));
        }
        sysLog.setBrowser(browser);
        sysLogRepository.save(sysLog);
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>(2);
                String key = parameters[i].getName();
                if (StringUtils.hasText(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.isEmpty()) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

    @Override
    public Page<SysLog> findAll(SysLog sysLog, Integer page, Integer size) {
        // 1.动态拼接查询条件
        Specification<SysLog> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(sysLog.getLogType())) {
                list.add(criteriaBuilder.like(root.get("logType").as(String.class), "%" + sysLog.getLogType()));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        // 2.构造分页
        Pageable pageable = PageRequest.of(page - 1, size);
        // 3.查询数据并返回
        Page<SysLog> pageLog = sysLogRepository.findAll(specification, pageable);
        return pageLog;
    }

    @Override
    public SysLog findById(Long id) {
        return sysLogRepository.findById(id).orElse(new SysLog());
    }
}
