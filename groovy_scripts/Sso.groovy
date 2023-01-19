class Sso {
    def ssoCommands
    def ssoAuthLogs
    def ssoUserLogs
    def ssoUsers
    def opzioneReset = "Reset"
    def opzioneElimina = "Elimina"
    def opzionePopola = "Popola"

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
            ssoCommands.initializeImages(script)
            if (script.params.sso_commands == opzioneReset) {
                ssoCommands.buildDockerImagesReset(script)
            } else if (script.params.sso_commands == opzioneElimina) {
                ssoCommands.buildDockerImageDelete(script)
            } else if (script.params.sso_commands == opzionePopola) {
                ssoCommands.buildDockerImagePopulate(script)
            }
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            if (script.params.sso_commands == opzioneReset) {
                ssoCommands.runDockerImagesReset(script)
            } else if (script.params.sso_commands == opzioneElimina) {
                ssoCommands.runDockerImageDelete(script)
            } else if (script.params.sso_commands == opzionePopola) {
                ssoCommands.runDockerImagePopulate(script)
            }
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            if (script.params.sso_commands == opzioneReset) {
                ssoCommands.stopDockerContainersReset(script)
            } else if (script.params.sso_commands == opzioneElimina) {
                ssoCommands.stopDockerContainerDelete(script)
            } else if (script.params.sso_commands == opzionePopola) {
                ssoCommands.stopDockerContainerPopulate(script)
            }
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.is_sso_commands) {
            if (script.params.sso_commands == opzioneReset) {
                ssoCommands.deleteDockerContainersReset(script)
            } else if (script.params.sso_commands == opzioneElimina) {
                ssoCommands.deleteDockerContainerDelete(script)
            } else if (script.params.sso_commands == opzionePopola) {
                ssoCommands.deleteDockerContainerPopulate(script)
            }
        }
    }
}

Sso createSso() {
    return new Sso()
}

return this