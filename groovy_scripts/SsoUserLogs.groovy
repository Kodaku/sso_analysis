class SsoUserLogs {
    def sso_user_logs_upsert_img
    def sso_user_logs_delete_img
    def sso_user_logs_upsert = "sso_user_logs_upsert"
    def sso_user_logs_delete = "sso_user_logs_delete"
    def sso_user_logs_upsert_registry
    def sso_user_logs_delete_registry
    def ssoUserLogsUpsertImage
    def ssoUserLogsDeleteImage
    def opzioneReset = "Reset"
    def opzioneElimina = "Elimina"
    def opzionePopola = "Popola"

    public void setEnvironment() {
        sso_user_logs_upsert_registry = "axelmastroianni/${this.sso_user_logs_upsert}"
        sso_user_logs_delete_registry = "axelmastroianni/${this.sso_user_logs_delete}"
        ssoUserLogsUpsertImage = ''
        ssoUserLogsDeleteImage = ''
    }

    public void initializeImages(def script) {
        sso_user_logs_delete_img = sso_user_logs_delete_registry + ":${script.env.BUILD_ID}"
        sso_user_logs_upsert_img = sso_user_logs_upsert_registry + ":${script.env.BUILD_ID}"
        println("${sso_user_logs_delete_img}")
        println("${sso_user_logs_upsert_img}")
    }

    public void buildDockerImages(def script) {
        initializeImages(script)
        if (script.params.sso_user_logs == opzioneReset) {
            ssoUserLogsUpsertImage = script.docker.build("${sso_user_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/populate_index/Dockerfile .")
            ssoUserLogsDeleteImage = script.docker.build("${sso_user_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/delete_index/Dockerfile .")
        } else if (script.params.sso_user_logs == opzioneElimina) {
            ssoUserLogsDeleteImage = script.docker.build("${sso_user_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/delete_index/Dockerfile .")
        } else if (script.params.sso_user_logs == opzionePopola) {
            ssoUserLogsUpsertImage = script.docker.build("${sso_user_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/populate_index/Dockerfile .")
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.sso_user_logs == opzioneReset) {
            script.powershell "docker run --name ${sso_user_logs_delete} ${sso_user_logs_delete_img}"
            script.powershell "docker run --name ${sso_user_logs_upsert} ${sso_user_logs_upsert_img}"
        } else if (script.params.sso_user_logs == opzioneElimina) {
            script.powershell "docker run --name ${sso_user_logs_delete} ${sso_user_logs_delete_img}"
        } else if (script.params.sso_user_logs == opzionePopola) {
            script.powershell "docker run --name ${sso_user_logs_upsert} ${sso_user_logs_upsert_img}"
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.sso_user_logs == opzioneReset) {
            script.powershell "docker stop ${sso_user_logs_delete}"
            script.powershell "docker stop ${sso_user_logs_upsert}"
        } else if (script.params.sso_user_logs == opzioneElimina) {
            script.powershell "docker stop ${sso_user_logs_delete}"
        } else if (script.params.sso_user_logs == opzionePopola) {
            script.powershell "docker stop ${sso_user_logs_upsert}"
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.sso_user_logs == opzioneReset) {
            script.powershell "docker rm ${sso_user_logs_delete}"
            script.powershell "docker rm ${sso_user_logs_upsert}"
        } else if (script.params.sso_user_logs == opzioneElimina) {
            script.powershell "docker rm ${sso_user_logs_delete}"
        } else if (script.params.sso_user_logs == opzionePopola) {
            script.powershell "docker rm ${sso_user_logs_upsert}"
        }
    }
}

SsoUserLogs createSsoUserLogs() {
    new SsoUserLogs()
}

return this