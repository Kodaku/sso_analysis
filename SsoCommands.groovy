class SsoCommands {
    def sso_commands_upsert_img
    def sso_commands_delete_img
    String sso_commands_upsert = "sso_commands_upsert"
    String sso_commands_delete = "sso_commands_delete"
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

    public void initializeImages() {
        sso_commands_delete_img = sso_commands_delete_registry + ":${env.BUILD_ID}"
        sso_commands_upsert_img = sso_commands_upsert_registry + ":${env.BUILD_ID}"
        println("${sso_commands_delete_img}")
        println("${sso_commands_upsert_img}")
    }

    public void buildDockerImages(def script) {
        ssoCommandsUpsertImage = script.docker.build("${sso_commands_upsert_img}", "-f ${script.env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
        ssoCommandsDeleteImage = script.docker.build("${sso_commands_delete_img}", "-f ${script.env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
    }

    public void runDockerImages() {
        powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
        powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
    }

    public void stopDockerContainers() {
        powershell "docker stop ${sso_commands_delete}"
        powershell "docker stop ${sso_commands_upsert}"
    }

    public void deleteDockerContainers() {
        powershell "docker rm ${sso_commands_delete}"
        powershell "docker rm ${sso_commands_upsert}"
    }
}

SsoCommands createSsoCommands() {
    new SsoCommands()
}

return this