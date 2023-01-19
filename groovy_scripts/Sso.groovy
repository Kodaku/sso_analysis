class Sso {
    GroovyShell shell = new GroovyShell()
    def ssoCommands = shell.parse(new File('./groovy_scripts/SsoCommands.groovy')).createSsoCommands()
    def ssoAuthLogs = shell.parse(new File('./groovy_scripts/SsoAuthLogs.groovy')).createSsoAuthLogs()
    def ssoUserLogs = shell.parse(new File('./groovy_scripts/SsoUserLogs.groovy')).createSsoUserLogs()
    def ssoUsers = shell.parse(new File('./groovy_scripts/SsoUsers.groovy')).createSsoUsers()

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