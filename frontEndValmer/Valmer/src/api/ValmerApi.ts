import axios from "axios";

const valmerApi = axios.create({
    baseURL: import.meta.env.VITE_APP_BASE_URL as string,
    headers: {
        'Access-Control-Allow-Origin': '*'
    }
});

valmerApi.interceptors.request.use( (config: any) => {
    return config;
});

export { valmerApi };

