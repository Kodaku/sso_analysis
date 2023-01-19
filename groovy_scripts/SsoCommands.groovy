class SsoCommands {
    def sso_commands_upsert_img
    def sso_commands_delete_img
    def sso_commands_upsert = "sso_commands_upsert"
    def sso_commands_delete = "sso_commands_delete"
    def sso_commands_upsert_registry
    def sso_commands_delete_registry
    def ssoCommandsUpsertImage
    def ssoCommandsDeleteImage

    public void setEnvironment() {
        sso_commands_upsert_registry = "axelmastroianni/${this.sso_commands_upsert}"
        sso_commands_delete_registry = "axelmastroianni/${this.sso_commands_delete}"
        ssoCommandsUpsertImage = ''
        ssoCommandsDeleteImage = ''
    }

    public void initializeImages(def script) {
        sso_commands_delete_img = sso_commands_delete_registry + ":${script.env.BUILD_ID}"
        sso_commands_upsert_img = sso_commands_upsert_registry + ":${script.env.BUILD_ID}"
        println("${sso_commands_delete_img}")
        println("${sso_commands_upsert_img}")
    }

    public void buildDockerImages(def script) {
        initializeImages(script)
        if (script.params.sso_commands == opzioneReset) {
            ssoCommandsUpsertImage = script.docker.build("${sso_commands_upsert_img}", "-f ${script.env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
            ssoCommandsDeleteImage = script.docker.build("${sso_commands_delete_img}", "-f ${script.env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
        } else if (script.params.sso_commands == opzioneElimina) {
            ssoCommandsDeleteImage = script.docker.build("${sso_commands_delete_img}", "-f ${script.env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
        } else if (script.params.sso_commands == opzionePopola) {
            ssoCommandsUpsertImage = script.docker.build("${sso_commands_upsert_img}", "-f ${script.env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.sso_commands == opzioneReset) {
            script.powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
            script.powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
        } else if (script.params.sso_commands == opzioneElimina) {
            script.powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
        } else if (script.params.sso_commands == opzionePopola) {
            script.powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.sso_commands == opzioneReset) {
            script.powershell "docker stop ${sso_commands_delete}"
            script.powershell "docker stop ${sso_commands_upsert}"
        } else if (script.params.sso_commands == opzioneElimina) {
            script.powershell "docker stop ${sso_commands_delete}"
        } else if (script.params.sso_commands == opzionePopola) {
            script.powershell "docker stop ${sso_commands_upsert}"
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.sso_commands == opzioneReset) {
            script.powershell "docker rm ${sso_commands_delete}"
            script.powershell "docker rm ${sso_commands_upsert}"
        } else if (script.params.sso_commands == opzioneElimina) {
            script.powershell "docker rm ${sso_commands_delete}"
        } else if (script.params.sso_commands == opzionePopola) {
            script.powershell "docker rm ${sso_commands_upsert}"
        }
    }
}

SsoCommands createSsoCommands() {
    new SsoCommands()
}

return this