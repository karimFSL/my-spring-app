pipeline {

    agent any

    tools {
        jdk 'jdk_update'
        maven 'mvn_363'
    }

    environment {

        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081"
        NEXUS_REPOSITORY = "repository-example"
        NEXUS_CREDENTIAL_ID = "nexus-credentials"
    }


    stages {
        stage('Clean & compile') { 
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') { 
            steps {
                sh 'mvn test'
            }
        }

        // deployer l'application sur le server de test iso a la prod 
        stage('Package and deploy application') { 
            steps {
                sh 'mvn package wildfly:deploy'
            }
        }


        // stage('Acceptance Tests') { 
        //     Cucumber/Robotframework/SoapUI
        //     steps {
        //         
        //     }
        // }


          stage("Publish to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "${env.WORKSPACE}/pom.xml";
                    filesByGlob = findFiles(glob: "**/target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;

                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "${env.WORKSPACE}/pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }
    }
 
}