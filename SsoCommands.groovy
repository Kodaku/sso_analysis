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
        sso_commands_upsert_registry = "axelmastroianni/${sso_commands_upsert}"
        sso_commands_delete_registry = "axelmastroianni/${sso_commands_delete}"
        ssoCommandsUpsertImage = ''
        ssoCommandsDeleteImage = ''
    }

    public void initializeImages() {
        sso_commands_delete_img = sso_commands_delete_registry + ":${env.BUILD_ID}"
        sso_commands_upsert_img = sso_commands_upsert_registry + ":${env.BUILD_ID}"
        println("${sso_commands_delete_img}")
        println("${sso_commands_upsert_img}")
    }
}