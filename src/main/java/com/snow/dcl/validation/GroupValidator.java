package com.snow.dcl.validation;

import javax.validation.groups.Default;
/**
 * @ClassName GroupValidator
 * (功能描述)
 * 数据返回对象
 * @Author Dcl_Snow
 * @Create 2021/8/25 10:42
 * @Version 1.0.0
 */
public interface GroupValidator extends Default {
        interface Create extends GroupValidator{

        }

        interface Update extends GroupValidator{

        }

        interface Query extends GroupValidator{

        }

        interface Delete extends GroupValidator{

        }
}
