import router from "@/router";

export function fetchUserInfo() {
}

export function getPhoto(url) {
    return `http://localhost:8082/api/picture/${url}`;
}
