FROM node:alpine AS build-stage
WORKDIR /app
COPY /frontend/package*.json ./
RUN npm ci
COPY /frontend/ ./
RUN npm run build


FROM node:alpine AS production-stage
WORKDIR /app
COPY --from=build-stage /app ./
EXPOSE 5173
CMD ["npm", "run", "dev", "--host"]
