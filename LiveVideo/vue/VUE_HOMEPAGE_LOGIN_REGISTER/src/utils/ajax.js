import axios from "axios"

let ajax = axios.create({
    baseURL: process.env.VUE_APP_BASE_API,
    timeout: 100000
});

ajax.interceptors.request.use(config => {
        config.headers = {
            token: localStorage.getItem('token'),
            // OrgId: auth.getUser().orgId,
            // UserId: auth.getUser().id,
        }
        return config
    },
    err => {
        return Promise.reject(err)
    })

let downloadFile = async (url, formData, options) => {
    await ajax.post(url, formData, {responseType: 'arraybuffer'}).then(resp => download(resp, options))
}

let getFile = async (url, options) => {
    await ajax.get(url, {responseType: 'blob'}).then(resp => download(resp, options))
}

let download = (resp, options) => {
    let blob = new Blob([resp.data], {type: options.fileType ? options.fileType : 'application/octet-binary'})
    //创建下载的链接
    let href = window.URL.createObjectURL(blob)
    downloadBlob(href, options.fileName)
}

let downloadBlob = (blobUrl, fileName, revokeObjectURL) => {
    let downloadElement = document.createElement('a')
    downloadElement.href = blobUrl
    //下载后文件名
    downloadElement.download = fileName
    document.body.appendChild(downloadElement)
    //点击下载
    downloadElement.click()
    //下载完成移除元素
    document.body.removeChild(downloadElement)
    if (revokeObjectURL == null || revokeObjectURL) {
        //释放掉blob对象
        window.URL.revokeObjectURL(blobUrl)
    }
}

let getDownloadFileUrl = async (url, fileType) => {
    let blob
    await ajax.get(url, {responseType: 'blob'}).then(resp => {
        blob = new Blob([resp.data], {type: fileType ? fileType : 'application/octet-binary'});
    })
    return window.URL.createObjectURL(blob);
}

let getDownloadFileUrlByPost = async (url, data, fileType) => {
    let blob
    await ajax.post(url, data, {responseType: 'blob'}).then(resp => {
        blob = new Blob([resp.data], {type: fileType ? fileType : 'application/octet-binary'});
    })
    return window.URL.createObjectURL(blob);
}

let getDownloadFileBlob = async (url, fileType) => {
    let blob
    await ajax.get(url, {responseType: 'blob'}).then(resp => {
        blob = new Blob([resp.data], {type: fileType ? fileType : 'application/octet-binary'});
    })
    return blob;
}

export default {
    ajax,
    downloadFile,
    getFile,
    getDownloadFileUrl,
    getDownloadFileUrlByPost,
    getDownloadFileBlob,
    downloadBlob
}
