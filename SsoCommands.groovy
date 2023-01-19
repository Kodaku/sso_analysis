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

    public void buildDockerImageDelete(def script) {
        ssoCommandsDeleteImage = script.docker.build("${sso_commands_delete_img}", "-f ${script.env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
    }

    public void buildDockerImagePopulate(def script) {
        ssoCommandsUpsertImage = script.docker.build("${sso_commands_upsert_img}", "-f ${script.env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
    }

    public void buildDockerImagesReset(def script) {
        ssoCommandsUpsertImage = script.docker.build("${sso_commands_upsert_img}", "-f ${script.env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
        ssoCommandsDeleteImage = script.docker.build("${sso_commands_delete_img}", "-f ${script.env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
    }

    public void runDockerImageDelete(def script) {
        script.powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
    }

    public void runDockerImagePopulate(def script) {
        script.powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
    }

    public void runDockerImagesReset(def script) {
        script.powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
        script.powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
    }

    public void stopDockerContainerElimina(def script) {
        script.powershell "docker stop ${sso_commands_delete}"
    }

    public void stopDockerContainerPopola(def script) {
        script.powershell "docker stop ${sso_commands_upsert}"
    }

    public void stopDockerContainersReset(def script) {
        script.powershell "docker stop ${sso_commands_delete}"
        script.powershell "docker stop ${sso_commands_upsert}"
    }

    public void deleteDockerContainerElimina(def script) {
        script.powershell "docker rm ${sso_commands_delete}"
    }

    public void deleteDockerContainerPopola(def script) {
        script.powershell "docker rm ${sso_commands_upsert}"
    }

    public void deleteDockerContainersReset(def script) {
        script.powershell "docker rm ${sso_commands_delete}"
        script.powershell "docker rm ${sso_commands_upsert}"
    }
}

SsoCommands createSsoCommands() {
    new SsoCommands()
}

return this