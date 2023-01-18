import SsoCommands

ssoCommands = SsoCommands()

pipeline {
    environment {
        ssoCommands.setEnvironment()
    }
    agent any

    stages {
        stage("Build image") {
            steps {
                script {
                    ssoCommands.initializeImages()
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