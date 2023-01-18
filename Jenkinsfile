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
                    if (params.sso_commands == "Reset") {
                        ssoCommandsUpsertImage = docker.build("${sso_commands_upsert_img}", "-f ${env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
                        ssoCommandsDeleteImage = docker.build("${sso_commands_delete_img}", "-f ${env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
                    }
                }
            }
        }

        stage("Testing - running in Jenkins node") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        powershell "docker run --name ${sso_commands_delete} ${sso_commands_delete_img}"
                        powershell "docker run --name ${sso_commands_upsert} ${sso_commands_upsert_img}"
                    }
                }
            }
        }

        stage("Stopping running container") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        powershell "docker stop ${sso_commands_delete}"
                        powershell "docker stop ${sso_commands_upsert}"
                    }
                }
            }
        }

        stage("Removing the container") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        powershell "docker rm ${sso_commands_delete}"
                        powershell "docker rm ${sso_commands_upsert}"
                    }
                }
            }
        }
    }
}