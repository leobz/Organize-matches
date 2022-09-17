FROM node:16-alpine

WORKDIR app/

COPY package*.json ./
RUN npm install
RUN apk add --no-cache bash
COPY . ./
# Agregar VOLUME para hotreload

CMD [ "npm", "start" ]