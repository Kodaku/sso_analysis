class SsoAuthLogs {
    def sso_auth_logs_upsert_img
    def sso_auth_logs_delete_img
    def sso_auth_logs_upsert = "sso_auth_logs_upsert"
    def sso_auth_logs_delete = "sso_auth_logs_delete"
    def sso_auth_logs_upsert_registry
    def sso_auth_logs_delete_registry
    def ssoAuthLogsUpsertImage
    def ssoAuthLogsDeleteImage

    public void setEnvironment() {
        sso_auth_logs_upsert_registry = "axelmastroianni/${this.sso_auth_logs_upsert}"
        sso_auth_logs_delete_registry = "axelmastroianni/${this.sso_auth_logs_delete}"
        ssoAuthLogsUpsertImage = ''
        ssoAuthLogsDeleteImage = ''
    }

    public void initializeImages(def script) {
        sso_auth_logs_delete_img = sso_auth_logs_delete_registry + ":${script.env.BUILD_ID}"
        sso_auth_logs_upsert_img = sso_auth_logs_upsert_registry + ":${script.env.BUILD_ID}"
        println("${sso_auth_logs_delete_img}")
        println("${sso_auth_logs_upsert_img}")
    }

    public void buildDockerImageDelete(def script) {
        ssoAuthLogsDeleteImage = script.docker.build("${sso_auth_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/delete_index/Dockerfile .")
    }

    public void buildDockerImagePopulate(def script) {
        ssoAuthLogsUpsertImage = script.docker.build("${sso_auth_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/populate_index/Dockerfile .")
    }

    public void buildDockerImagesReset(def script) {
        ssoAuthLogsUpsertImage = script.docker.build("${sso_auth_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/populate_index/Dockerfile .")
        ssoAuthLogsDeleteImage = script.docker.build("${sso_auth_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/delete_index/Dockerfile .")
    }

    public void runDockerImageDelete(def script) {
        script.powershell "docker run --name ${sso_auth_logs_delete} ${sso_auth_logs_delete_img}"
    }

    public void runDockerImagePopulate(def script) {
        script.powershell "docker run --name ${sso_auth_logs_upsert} ${sso_auth_logs_upsert_img}"
    }

    public void runDockerImagesReset(def script) {
        script.powershell "docker run --name ${sso_auth_logs_delete} ${sso_auth_logs_delete_img}"
        script.powershell "docker run --name ${sso_auth_logs_upsert} ${sso_auth_logs_upsert_img}"
    }

    public void stopDockerContainerDelete(def script) {
        script.powershell "docker stop ${sso_auth_logs_delete}"
    }

    public void stopDockerContainerPopulate(def script) {
        script.powershell "docker stop ${sso_auth_logs_upsert}"
    }

    public void stopDockerContainersReset(def script) {
        script.powershell "docker stop ${sso_auth_logs_delete}"
        script.powershell "docker stop ${sso_auth_logs_upsert}"
    }

    public void deleteDockerContainerDelete(def script) {
        script.powershell "docker rm ${sso_auth_logs_delete}"
    }

    public void deleteDockerContainerPopulate(def script) {
        script.powershell "docker rm ${sso_auth_logs_upsert}"
    }

    public void deleteDockerContainersReset(def script) {
        script.powershell "docker rm ${sso_auth_logs_delete}"
        script.powershell "docker rm ${sso_auth_logs_upsert}"
    }
}

SsoAuthLogs createSsoAuthLogs() {
    new SsoAuthLogs()
}

return this