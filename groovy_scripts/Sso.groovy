class Sso {
    def ssoCommands
    def ssoAuthLogs
    def ssoUserLogs
    def ssoUsers

    public void setup(def script) {
        def ssoCommandsScript = script.load "./groovy_scripts/SsoCommands.groovy"
        def ssoAuthLogsScript = script.load "./groovy_scripts/SsoAuthLogs.groovy"
        def ssoUserLogsScript = script.load "./groovy_scripts/SsoUserLogs.groovy"
        def ssoUsersScript = script.load "./groovy_scripts/SsoUsers.groovy"
        ssoCommands = ssoCommandsScript.createSsoCommands()
        ssoAuthLogs = ssoAuthLogsScript.createSsoAuthLogs()
        ssoUserLogs = ssoUserLogsScript.createSsoUserLogs()
        ssoUsers = ssoUsersScript.createSsoUsers()
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

    public void buildDockerImages(def script) {
        if (script.params.is_sso_commands) {
            ssoCommands.buildDockerImages(script)
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            ssoCommands.runDockerContainers(script)
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            ssoCommands.stopDockerContainers(script)
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            ssoCommands.removeDockerContainers(script)
        }
    }
}

Sso createSso() {
    return new Sso()
}

return this