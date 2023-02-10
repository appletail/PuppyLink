pipeline{
    agent any
    tools {
        maven 'M3'
        dockerTool 'docker' 
    }
    stages{
        stage('checkout') {
            steps {
                sh 'docker -v'
                sh 'mvn -v'
                // gitlab에서 pull 가져오기
                git branch: 'chore/BE/jenkins-config',
                    credentialsId: 'puppys',
                    url: 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12C107.git'
            }
        }
        stage('build') {
            steps {
                // 각 폴더에 있는 파일을 이용해서 image 생성
                // 1. DB 및 redis image 만들기
                echo '------- mariadb 10.6.11 image ------'
                dir("db") {
                    sh 'docker build --pull=false -t puppy-db .'    
                }
                // 3. springboot image 만들기
                // springboot build 하기
                dir("back") {
                    sh 'mvn -v'
                    sh 'mvn clean install'
                    sh 'docker build --pull=false -t puppy-spring .'
                }
            }
        }
        // stage("deplop database") {
        //     steps {
        //         sh 'docker ps -q -f name=puppy-db | grep -q . && docker stop puppy-db'
        //         // sh 'docker stop $(docker ps -a -q -f name=spring)'
        //         // 이미지를 가지고 run처리 (mysql)
        //         sh 'docker run -d -p 3306:3306 \
        //                         -v mysql:/var/lib/mysql \
        //                         --net puppy-net \
        //                         --name puppy-db \
        //                         --rm puppy-db'
        //     }
        // }
        // stage('deploy redis') {
        //     steps {
        //         // 실행 중인 container stop
        //         sh 'docker ps -q -f name=puppy-redis | grep -q . && docker stop puppy-redis'
        //         // 이미지를 가지고 run처리 (redis)
        //         sh 'docker run -d -p 6379:6379 \
        //                         --net puppy-net \
        //                         --name puppy-redis \
        //                         --rm redis'
        //     }
        // }
        // stage('deploy spring'){
        //     steps {
        //         // 실행중인 puppy-spring 컨테이너 제거
        //         sh 'docker ps -q -f name=puppy-spring | grep -q . && docker stop puppy-spring'
        //         // 이미지를 가지고 run처리 (spring)
        //         sh 'docker run -d -p 8085:8085 \
        //                         --net puppy-net \
        //                         --name puppy-spring \
        //                         --rm puppy-spring'
        //         // 사용하지 않는 이미지 제거
        //         sh 'docker rmi $(docker images -f "dangling=true" -q)'
        //     }
        // }
    }
}