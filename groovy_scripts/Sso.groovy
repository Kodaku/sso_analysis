class Sso {
    GroovyShell shell = new GroovyShell()
    def ssoCommands = shell.parse(new File('SsoCommands.groovy')).createSsoCommands()
    def ssoAuthLogs = shell.parse(new File('SsoAuthLogs.groovy')).createSsoAuthLogs()
    def ssoUserLogs = shell.parse(new File('SsoUserLogs.groovy')).createSsoUserLogs()
    def ssoUsers = shell.parse(new File('SsoUsers.groovy')).createSsoUsers()

    public void setEnvironment(def script) {
        if (script.params.is_sso_commands) {
//             ssoCommands = commandsScript.createSsoCommands()
            ssoCommands = new SsoCommands()
            ssoCommands.setEnvironment()
        }
        if (script.params.is_sso_auth_logs) {
//             ssoCommands = commandsScript.createSsoCommands()
            ssoAuthLogs = new SsoAuthLogs()
            ssoAuthLogs.setEnvironment()
        }
        if (script.params.is_sso_user_logs) {
//             ssoCommands = commandsScript.createSsoCommands()
            ssoUserLogs = new SsoUserLogs()
            ssoUserLogs.setEnvironment()
        }
        if (script.params.is_sso_users) {
//             ssoCommands = commandsScript.createSsoCommands()
            ssoUsers = new SsoUsers()
            ssoUsers.setEnvironment()
        }
    }
}