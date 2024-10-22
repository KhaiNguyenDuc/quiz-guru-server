<#import "template.ftl" as layout>
<#import "components/link/primary.ftl" as linkPrimary>

<@layout.registrationLayout displayInfo=social.displayInfo; section>
<#if section = "title">
${msg("loginTitle",(realm.displayName!''))}
<#elseif section = "header">
    <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet"/>


<#elseif section = "form">

<div class="box-container">
    <div>
        <p class="application-name">Forget password?</p>
    </div>
    <#if realm.password>
        <form id="kc-reset-password-form" class="${properties.kcFormClass!} form" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <div class="${properties.kcLabelWrapperClass!}">
                    <label for="username" class="${properties.kcLabelClass!}"><#if !realm.loginWithEmailAllowed>${msg("username")}<#elseif !realm.registrationEmailAsUsername>${msg("usernameOrEmail")}<#else>${msg("email")}</#if></label>
                </div>
                <div class="${properties.kcInputWrapperClass!}">
                    <input type="text" id="username" name="username" class="${properties.kcInputClass!} login-field" autofocus value="${(auth.attemptedUsername!'')}" aria-invalid="<#if messagesPerField.existsError('username')>true</#if>" dir="ltr"/>
                    <#if messagesPerField.existsError('username')>
                        <span id="input-error-username" class="${properties.kcInputErrorMessageClass!}" aria-live="polite">
                                    ${kcSanitize(messagesPerField.get('username'))?no_esc}
                        </span>
                    </#if>
                </div>
            </div>
            <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!} cancel-btn">
                        <span><a href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))?no_esc}</a></span>
                    </div>
                    <div id="kc-form-buttons reset-password-form" class="${properties.kcFormButtonsClass!}">
                        <input class="submit reset-password-btn" type="submit" value="${msg("Reset Password")}" tabindex="3">
                    </div>
                </div>


            </div>
        </form>


    </#if>
    <div>
        <p class="copyright">copyright</p>
    </div>
</#if>
</@layout.registrationLayout>
