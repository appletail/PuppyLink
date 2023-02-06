pipeline{
    agent any
    tools {
        maven 'M3'
        dockerTool 'docker' 
    }
    environment {
        MY_BRANCH = "chore/BE/jenkins-config"
        MYHUB = ''
    }
    stages{
        stage('checkout') {
            steps {
                sh 'docker -v'
                sh 'mvn -v'
            // gitlab에서 pull 해오기
                git branch: 'chore/BE/jenkins-config',
                    credentialsId: 'puppys',
                    url: 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12C107.git'
            }
        }
        stage('ps stop') {
            steps {
                sh 'docker stop redis'
                sh 'docker stop mariadb'
            }
        }
        stage('build') {
            steps {
                // 각 폴더에 있는 파일을 이용해서 image 생성
                // 1. DB 및 redis image 만들기
                echo '------- mariadb 10.6.11 image ------'
                sh 'docker build --pull=false -t puppy-mariadb ./db'
                // 3. springboot image 만들기
                // springboot build 하기
                dir("back") {
                    sh 'mvn -v'
                    sh 'mvn clean install'
                    sh 'docker build --pull=false -t puppy-spring .'
                }
            }
        }

        stage('deploy') {
            steps {
                // 이미지를 가지고 run처리 (mysql)
                sh 'docker run -d -p 3306:3306 \
                                  -v mariadb:/var/lib/mysql \
                                  --name mariadb \
                                  --rm puppy-mariadb'
                // 이미지를 가지고 run처리 (redis)
                sh 'docker run -d -p 6379:6379 \
                                  --name redis \
                                  --rm redis'
                // 이미지를 가지고 run처리 (spring)
                sh 'docker run -d -p 8085:8085 \
                                  --name spring \
                                  --rm puppy-spring'
            }
        }
    }
}