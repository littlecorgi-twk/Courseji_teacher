package com.littlecorgi.my.ui.signin;

import androidx.annotation.Nullable;

/**
 * 登录表单的数据验证状态。
 *
 * @author littlecorgi 2021/05/02
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}