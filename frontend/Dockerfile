FROM node:16-alpine

WORKDIR app/

COPY package*.json ./
RUN npm install
COPY . ./
# Agregar VOLUME para hotreload

CMD [ "npm", "start" ]