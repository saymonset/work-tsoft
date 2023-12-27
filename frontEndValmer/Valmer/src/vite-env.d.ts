/// <reference types="vite/client" />

interface ImportMetaEnv {
    VITE_APP_BASE_URL?: string;
}

interface ImportMeta {
    env: ImportMetaEnv;
}