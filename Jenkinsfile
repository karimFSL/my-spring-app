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
        stage('compile') { 
            steps {
                sh 'mvn compile'
            }
        }

        stage('Unit Tests') { 
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') { 
            steps {
                sh 'mvn package'
            }
        }

        // stage('Deploy') { 
        //      steps {
        //     }
        // }

    }
 
}