pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage('checkout') {
            steps {
                git branch: 'release',
                    credentialsId: "puppys",
                    url: 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12C107.git'
            }
        }
        stage('build') {
            steps {
                dir('back') {
                    sh 'mvn -v'
                    sh 'mvn clean package'
                    sh 'docker build -t puppy-spring .'
                }
            }
        }
    }
}
