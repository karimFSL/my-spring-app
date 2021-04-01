node {


    stage('Test') { 
        print "${env.BRANCH_NAME}"
    }

      stage('compile') { 
        sh 'mvn compile'
    }

     stage('Unit Tests') { 
       sh 'mvn test'
    }

     stage('Package') { 
       sh 'mvn package'
    }

     stage('Deploy') { 
     
    }
}