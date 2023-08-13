FROM nginx:1.23.4-alpine

EXPOSE 80
RUN sed -i -r '/(\s+)index(\s+)index/a try_files $uri $uri/ /index.html;' /etc/nginx/conf.d/default.conf

COPY dist /usr/share/nginx/html
