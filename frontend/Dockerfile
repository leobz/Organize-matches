FROM node:16-alpine

WORKDIR /app

COPY package*.json ./
RUN npm install react-router-dom localforage match-sorter sort-by
COPY . ./

CMD [ "npm", "start" ]