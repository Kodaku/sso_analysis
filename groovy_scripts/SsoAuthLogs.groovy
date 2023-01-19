class SsoAuthLogs {
    def sso_auth_logs_upsert_img
    def sso_auth_logs_delete_img
    def sso_auth_logs_upsert = "sso_auth_logs_upsert"
    def sso_auth_logs_delete = "sso_auth_logs_delete"
    def sso_auth_logs_upsert_registry
    def sso_auth_logs_delete_registry
    def ssoAuthLogsUpsertImage
    def ssoAuthLogsDeleteImage
    def opzioneReset = "Reset"
    def opzioneElimina = "Elimina"
    def opzionePopola = "Popola"

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

    public void buildDockerImages(def script) {
        initializeImages(script)
        if (script.params.sso_auth_logs == opzioneReset) {
            ssoAuthLogsUpsertImage = script.docker.build("${sso_auth_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/populate_index/Dockerfile .")
            ssoAuthLogsDeleteImage = script.docker.build("${sso_auth_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/delete_index/Dockerfile .")
        } else if (script.params.sso_auth_logs == opzioneElimina) {
            ssoAuthLogsDeleteImage = script.docker.build("${sso_auth_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/delete_index/Dockerfile .")
        } else if (script.params.sso_auth_logs == opzionePopola) {
            ssoAuthLogsUpsertImage = script.docker.build("${sso_auth_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_auth_logs/populate_index/Dockerfile .")
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.sso_auth_logs == opzioneReset) {
            script.powershell "docker run --name ${sso_auth_logs_delete} ${sso_auth_logs_delete_img}"
            script.powershell "docker run --name ${sso_auth_logs_upsert} ${sso_auth_logs_upsert_img}"
        } else if (script.params.sso_auth_logs == opzioneElimina) {
            script.powershell "docker run --name ${sso_auth_logs_delete} ${sso_auth_logs_delete_img}"
        } else if (script.params.sso_auth_logs == opzionePopola) {
            script.powershell "docker run --name ${sso_auth_logs_upsert} ${sso_auth_logs_upsert_img}"
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.sso_auth_logs == opzioneReset) {
            script.powershell "docker stop ${sso_auth_logs_delete}"
            script.powershell "docker stop ${sso_auth_logs_upsert}"
        } else if (script.params.sso_auth_logs == opzioneElimina) {
            script.powershell "docker stop ${sso_auth_logs_delete}"
        } else if (script.params.sso_auth_logs == opzionePopola) {
            script.powershell "docker stop ${sso_auth_logs_upsert}"
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.sso_auth_logs == opzioneReset) {
            script.powershell "docker rm ${sso_auth_logs_delete}"
            script.powershell "docker rm ${sso_auth_logs_upsert}"
        } else if (script.params.sso_auth_logs == opzioneElimina) {
            script.powershell "docker rm ${sso_auth_logs_delete}"
        } else if (script.params.sso_auth_logs == opzionePopola) {
            script.powershell "docker rm ${sso_auth_logs_upsert}"
        }
    }
}

SsoAuthLogs createSsoAuthLogs() {
    new SsoAuthLogs()
}

return this