import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    base: "/",
    plugins: [react()],
    preview: {
        port: 7900,
        strictPort: true,
    },
    server: {
        port: 7900,
        strictPort: true,
        host: true,
    },
});
