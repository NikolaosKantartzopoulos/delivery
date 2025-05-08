# Stage 1: Build the Vite app using a glibc-based image
FROM node:18-slim AS builder

WORKDIR /app

# Declare build argument
ARG TARGET_ENV

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy app source
COPY . .

# Clean and rebuild native dependencies (optional but safer)
RUN rm -rf node_modules package-lock.json && npm install && npm rebuild

# Build with the specified environment mode
RUN npm run build -- --mode ${TARGET_ENV}

# Stage 2: Serve static content with a minimal image
FROM node:18-slim

WORKDIR /app

# Install `serve` to serve the frontend
RUN npm install -g serve

# Copy built files
COPY --from=builder /app/dist ./dist

# Expose and serve on port 7900
ENV PORT=7900
EXPOSE 7900

CMD ["serve", "-s", "dist", "-l", "7900"]
