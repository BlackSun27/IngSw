FROM node:18-alpine AS build

WORKDIR /app

COPY package.json ./
COPY vite.config.js ./
RUN npm install

COPY . ./
RUN npm run build

FROM nginx:1.21.5-alpine AS release

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/dist /usr/share/nginx/html/

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]