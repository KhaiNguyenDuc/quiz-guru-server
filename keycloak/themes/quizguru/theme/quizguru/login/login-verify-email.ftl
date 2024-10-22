<#import "template.ftl" as layout>
<#import "components/link/primary.ftl" as linkPrimary>

<@layout.registrationLayout displayInfo=social.displayInfo; section>
<#if section = "title">
${msg("loginTitle",(realm.displayName!''))}
<#elseif section = "header">
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
    </script>

<#elseif section = "form">

<div class="box-container">
    <div>
        <p class="application-name">Verify email</p>
    </div>
    <#if realm.password>
        <div>
            <form id="kc-form-login" class="form verify-form" onsubmit="return true;">
                <p class="instruction">${msg("emailVerifyInstruction1",user.email)}</p>
                <p class="instruction">
                    ${msg("emailVerifyInstruction2")}
                    <br/>
                    <a href="${url.loginAction}">${msg("doClickHere")}</a> ${msg("emailVerifyInstruction3")}
                </p>
            </form>
            <div id="kc-form-options" class="backtologin">
                <a class="" href="${url.loginUrl}">${kcSanitize(msg("backToLogin"))?no_esc}</a>
            </div>
        </div>


    </#if>
    <div>
        <p class="copyright">copyright-2024</p>
    </div>
    </#if>
    </@layout.registrationLayout>
