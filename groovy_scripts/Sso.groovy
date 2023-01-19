class Sso {
    def ssoCommands
    def ssoAuthLogs
    def ssoUserLogs
    def ssoUsers

    public void setup(def script) {
        GroovyShell shell = new GroovyShell()
        def ssoCommandsScript = script.load "./groovy_scripts/SsoCommands.groovy"
        def ssoAuthLogsScript = script.load "./groovy_scripts/SsoAuthLogs.groovy"
        def ssoUserLogsScript = script.load "./groovy_scripts/SsoUserLogs.groovy"
        def ssoUsersScript = script.load "./groovy_scripts/SsoUsers.groovy"
        ssoCommands = ssoCommandsScript.createSsoCommands()
        ssoAuthLogs = ssoAuthLogsScript.createSsoAuthLogs()
        ssoUserLogs = ssoUserLogsScript.createSsoUserLogs()
        ssoUsers = ssoUsersScript.createSsoUsers()
//         ssoCommands = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoCommands.groovy')).createSsoCommands()
//         ssoAuthLogs = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoAuthLogs.groovy')).createSsoAuthLogs()
//         ssoUserLogs = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoUserLogs.groovy')).createSsoUserLogs()
//         ssoUsers = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoUsers.groovy')).createSsoUsers()
    }

    public void setEnvironment(def script) {
        if (script.params.is_sso_commands) {
            ssoCommands.setEnvironment()
        }
        if (script.params.is_sso_auth_logs) {
            ssoAuthLogs.setEnvironment()
        }
        if (script.params.is_sso_user_logs) {
            ssoUserLogs.setEnvironment()
        }
        if (script.params.is_sso_users) {
            ssoUsers.setEnvironment()
        }
    }
}

Sso createSso() {
    return new Sso()
}

return this