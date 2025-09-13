import axios from "axios";
import config from "./config";
import {} from "../requests/uploadRequest";

export const initiateUpload = async (fileName,fileSize,mimeType,ownerId,chunks) => {
    return axios.post(`${config.BASE_URL}/v1/file`,buildInitiateUploadRequest(fileName,fileSize,mimeType,ownerId,chunks));
}