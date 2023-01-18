def sso_commands_upsert_img
def sso_commands_delete_img
def sso_commands_upsert = "sso_commands_upsert"
def sso_commands_delete = "sso_commands_delete"
pipeline {
    environment {
        sso_commands_upsert_registry = "axelmastroianni/${sso_commands_upsert}"
        sso_commands_delete_registry = "axelmastroianni/${sso_commands_delete}"
        ssoCommandsUpsertImage = ''
        ssoCommandsDeleteImage = ''
    }
    agent any

    stages {
        stage("Build image") {
            steps {
                script {
                    sso_commands_delete_img = sso_commands_delete_registry + ":${env.BUILD_ID}"
                    sso_commands_upsert_img = sso_commands_upsert_registry + ":${env.BUILD_ID}"
                    println("${sso_commands_delete_img}")
                    println("${sso_commands_upsert_img}")
                    ssoCommandsUpsertImage = docker.build("${sso_commands_upsert_img}", "-f ${env.WORKSPACE}/sso_commands/populate_index/Dockerfile")
                    ssoCommandsDeleteImage = docker.build("${sso_commands_delete_img}", "-f ${env.WORKSPACE}/sso_commands/delete_index/Dockerfile")
                }
            }
        }

        stage("Testing - running in Jenkins node") {
            steps {
                powershell "docker run --name ${container_name} ${img}"
            }
        }

        stage("Stopping running container") {
            steps {
                powershell "docker stop ${container_name}"
            }
        }

        stage("Removing the container") {
            steps {
                powershell "docker rm ${container_name}"
            }
        }
    }
}