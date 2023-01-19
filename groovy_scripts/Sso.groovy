class Sso {
    GroovyShell shell = new GroovyShell()
    def ssoCommands
    def ssoAuthLogs
    def ssoUserLogs
    def ssoUsers

    public void setup(def script) {
        ssoCommands = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoCommands.groovy')).createSsoCommands()
        ssoAuthLogs = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoAuthLogs.groovy')).createSsoAuthLogs()
        ssoUserLogs = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoUserLogs.groovy')).createSsoUserLogs()
        ssoUsers = shell.parse(new File('${script.env.WORKSPACE}/groovy_scripts/SsoUsers.groovy')).createSsoUsers()
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