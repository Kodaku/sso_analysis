import SsoCommands
import SsoAuthLogs
import SsoUserLogs
import SsoUsers

class Sso {
    SsoCommands ssoCommands
    SsoAuthLogs ssoAuthLogs
    SsoUserLogs ssoUserLogs
    SsoUsers ssoUsers

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