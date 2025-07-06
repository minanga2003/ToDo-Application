# 1) Build stage: compile React
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 2) Runtime stage: serve with nginx
FROM nginx:stable-alpine
# remove default site
RUN rm /etc/nginx/conf.d/default.conf
# copy in a basic nginx.conf (you can tweak if needed)
COPY nginx.conf /etc/nginx/nginx.conf
# copy build output
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
