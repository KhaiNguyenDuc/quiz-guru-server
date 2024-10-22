<#import "template.ftl" as layout>
<#import "password-commons.ftl" as passwordCommons>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('password','password-confirm'); section>
    <#if section = "header">
<link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet"/>
    <script>
        function togglePassword() {
            var x = document.getElementById("password");
            var v = document.getElementById("vi");
            if (x.type === "password") {
                x.type = "text";
                v.src = "${url.resourcesPath}/img/eye.png";
            } else {
                x.type = "password";
                v.src = "${url.resourcesPath}/img/eye-off.png";
            }
        }

        function toggleConfirmPassword() {

            var y = document.getElementById("password-confirm");

            var v1 = document.getElementById("vi1");

            if (y.type === "password") {
                y.type = "text";
                v1.src = "${url.resourcesPath}/img/eye.png";
            } else {
                y.type = "password";
                v1.src = "${url.resourcesPath}/img/eye-off.png";
            }
        }
    </script>
    <#elseif section = "form">
        <div class="box-container">
        <div>
            <p class="application-name">Set new password</p>
        </div>
    <#if realm.password>
        <form id="kc-reset-password-form" class="${properties.kcFormClass!} form" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">

                <div class="${properties.kcInputWrapperClass!}">
                    <div class="${properties.kcInputGroup!}" dir="ltr">
                        <div>
                            <label class="visibility" id="v" onclick="togglePassword()"><img id="vi" src="${url.resourcesPath}/img/eye-off.png"></label>
                        </div>
                        <input placeholder="Password new" type="password" id="password" name="password-new" class="login-field ${properties.kcInputClass!}"
                               autofocus autocomplete="new-password"
                               aria-invalid="<#if messagesPerField.existsError('password','password-confirm')>true</#if>">
                    </div>

                    <#if messagesPerField.existsError('password')>
                        <span id="input-error-password" class="aria-invalid-message ${properties.kcInputErrorMessageClass!}" aria-live="polite">
                            ${kcSanitize(messagesPerField.get('password'))?no_esc}
                        </span>
                    </#if>
                </div>
            </div>
            <div class="${properties.kcFormGroupClass!}">

                <div class="${properties.kcInputWrapperClass!}">
                    <div class="${properties.kcInputGroup!}" dir="ltr">
                        <div>
                            <label class="visibility" id="v2" onclick="toggleConfirmPassword()"><img id="vi1" src="${url.resourcesPath}/img/eye-off.png"></label>
                        </div>
                        <input placeholder="Password confirm" type="password" id="password-confirm" name="password-confirm"
                               class="${properties.kcInputClass!} login-field"
                               autocomplete="new-password"
                               aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>">

                    </div>


                    <#if messagesPerField.existsError('password-confirm')>
                        <span id="input-error-password-confirm" class="aria-invalid-message ${properties.kcInputErrorMessageClass!}" aria-live="polite">
                            ${kcSanitize(messagesPerField.get('password-confirm'))?no_esc}
                        </span>
                    </#if>

                </div>
            </div>

            <div class="${properties.kcFormGroupClass!} update-password-form">
                <@passwordCommons.logoutOtherSessions/>
                <div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
                    <input class="submit reset-password-btn password-submit" type="submit" value="${msg("doSubmit")}" />
                </div>
            </div>

        </form>
    </#if>
        <div>
            <p class="copyright">copyright-2024</p>
        </div>

        <script type="module" src="${url.resourcesPath}/js/passwordVisibility.js"></script>
    </#if>
</@layout.registrationLayout>