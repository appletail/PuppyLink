FROM node:18.12.1-alpine as build-stage
# 작업 폴더를 만들고 npm 설치
WORKDIR /app
# ENV PATH /app/node_modules/.bin:$PATH
COPY package.json /app/package.json
RUN npm install
# react 등 필요한 API 설치하기
# RUN npm install react-scripts@2.1.3 -g
# 소스를 작업폴더로 복사하고 빌드
COPY . /app
RUN npm run build

FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
# 작성한 nginx 설정파일을 복사한다. 
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]