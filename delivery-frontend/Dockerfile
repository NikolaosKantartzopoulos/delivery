# Stage 1: Build the Vite app
FROM node:18-alpine AS builder

WORKDIR /app

# Declare build argument
ARG TARGET_ENV

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy app source
COPY . .

# Build with environment mode
RUN npm run build -- --mode ${TARGET_ENV}

# Stage 2: Serve static content with a minimal image
FROM node:18-alpine

WORKDIR /app

# Install `serve` to serve the frontend
RUN npm install -g serve

# Copy built files
COPY --from=builder /app/dist ./dist

# Expose and serve on port 7900
ENV PORT=7900
EXPOSE 7900

CMD [ "serve", "-s", "dist", "-l", "7900" ]
