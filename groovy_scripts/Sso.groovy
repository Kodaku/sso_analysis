class Sso {
    GroovyShell shell = new GroovyShell()
    def ssoCommands = shell.parse(new File('SsoCommands.groovy')).createSsoCommands()
    def ssoAuthLogs = shell.parse(new File('SsoAuthLogs.groovy')).createSsoAuthLogs()
    def ssoUserLogs = shell.parse(new File('SsoUserLogs.groovy')).createSsoUserLogs()
    def ssoUsers = shell.parse(new File('SsoUsers.groovy')).createSsoUsers()

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