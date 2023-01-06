import router from "@/router";

export function fetchUserInfo() {
}

export function getPhoto(url) {
    if (url.includes('http'))
        return url;
    else
        return `http://localhost:8082/api/picture/${url}`;
}

export function getFile(url) {
    if (url.includes('http'))
        return url;
    else {
        return `http://localhost:8082/api/file/${url}`;
    }
}
