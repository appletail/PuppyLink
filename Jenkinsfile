pipeline{
    agent any
    tools {
        maven 'M3'
        dockerTool 'Tomkins'
    }
    environment {
        registCredential = "qlwms221"
        springImg = ""
        nginxImg = ""
        mariaImg = ""
        ver = "0.7"
    }
    stages{
        stage('checkout') {
            steps {
                sh 'mvn -v'
                // gitlab에서 pull 가져오기 (성공)
                git branch: 'test/BE/jenkins-config',
                    credentialsId: 'ssafy_project_puppylink',
                    url: 'https://lab.ssafy.com/s08-webmobile2-sub2/S08P12C107.git'
            }
            post {
                success {
                    echo 'Seccussfully Cloned Respository'
                }
                failure {
                    error 'this pipeline stops here...'
                }
            }
        }
        stage('build') {
            steps {
                echo 'build spring boot .jar start'
                sh 'cp /var/jenkins_home/workspace/initfile/application-dev.yml ./back/src/main/resources/application-dev.yml'
                sh 'mvn -v'
                dir("back") {
                    sh 'mvn clean compile install'
                    script {
                        springImg = docker.build registCredential + "/spring:$ver"
                    } 
                }
                echo 'build nginx-react build start'
                dir("front") {
                    script {
                        nginxImg = docker.build registCredential + "/nginx:$ver"
                    }
                }
                echo 'build database build start'
                dir("db") {
                    script {
                        mariaImg = docker.build registCredential + "/mariadb:$ver"
                    }
                }
            }
            post {
                success {
                    echo 'jenkins build success OK'
                    sh 'docker image prune --force'
                }
                failure {
                    echo 'jenkins build fail'
                }
            }
        }
        stage('containter stop') {
            parallel {
                stage('stop nginx') {
                    steps {
                        sh 'docker ps -f name=puppy-nginx -q | xargs --no-run-if-empty docker container stop'
                        sh 'docker container ls -a -f name=puppy-nginx -q | xargs -r docker container rm'
                    }
                    post {
                        failure {
                            echo "not exist $nginxImg"
                        }
                    }
                }
                stage('stop spring') {
                    steps {
                        sh 'docker ps -f name=puppy-spring -q | xargs --no-run-if-empty docker container stop'
                        sh 'docker container ls -a -f name=puppy-spring -q | xargs -r docker container rm'
                    }
                    post {
                        failure {
                            echo "not exist $springImg"
                        }
                    }
                }
                stage('stop mariadb') {
                    steps {
                        sh 'docker ps -f name=puppy-db -q | xargs --no-run-if-empty docker container stop'
                        sh 'docker container ls -a -f name=puppy-mariadb -q | xargs -r docker container rm'
                    }
                    post {
                        failure {
                            echo "not exist $mariaImg"
                        }
                    }
                }
                stage('stop redis') {
                    steps {
                        sh 'docker ps -f name=puppy-redis -q | xargs --no-run-if-empty docker container stop'
                        sh 'docker container ls -a -f name=puppy-redis -q | xargs -r docker container rm'
                    }
                    post {
                        failure {
                            echo 'not exist redis-container'
                        }
                    }
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    try {
                        echo 'puppy-link database create container start'
                        sh "docker run -d -p 6379:6379 \
                                    --name puppy-redis \
                                    --net puppy-net \
                                    --rm redis"
                        sh "docker run -d -p 3306:3306 \
                                    -v mariadb:/var/lib/mysql \
                                    -v /var/mariadb/init:/docker-entrypoint-initdb.d \
                                    --name puppy-db \
                                    --net puppy-net \
                                    --rm qlwms221/mariadb:$ver"
                        echo 'puppy-link spring create container start'
                        sh "docker run -d -p 8085:8085 \
                                    --name puppy-spring \
                                    --net puppy-net \
                                    --rm qlwms221/spring:$ver"
                        echo 'puppy-link nginx create container start'
                        sh "docker run -d -p 3000:80 \
                                    --name puppy-nginx \
                                    --net puppy-net \
                                    --rm qlwms221/nginx:$ver"
                    } catch(error) {
                        print(error)
                    }
                }
            }
            post {
                success {
                    sh 'docker image prune --all --force'
                }
            }
        }
        // stage('build image') {
        //     steps {
        //         dir("db") {
        //             echo "docker image build mariadb $ver"
        //             script {
        //                 mariaImg = docker.build registCredential + "/db:$ver"
        //             }
        //         }
        //         dir("back") {
        //             echo "docker image build spring $ver"
        //             sh "mvn clean comfile install"
        //             script {
        //                 springImg = docker.build registCredential + "/spring:$ver"
        //             }
        //         }
        //         dir("front") {
        //             echo "docker image build nginx&react $ver"
        //             script {
        //                 nginxImg = docker.build registCredential + "/nginx:$ver"
        //             }
        //         }
        //     }
        //     post {
        //         failure {
        //             error 'this pipeline stops here...'
        //         }
        //     }
        // }
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