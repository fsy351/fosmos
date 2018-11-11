
import axios from 'axios';

axios.interceptors.request.use(
    config=>{
        return config;
    },
    error => {
        Promise.reject(error);
    }
);

axios.interceptors.response.use(
    resp =>{
        return resp;
    },
    error => {
        Promise.reject(error);
    }
);

export default function(config){
    return axios.create({
        headers:{
            'Content-Type': 'application/json'
        }
    }).request(
        config
    ).then(resp => {
        return resp.data;
    }).then(data => {
        return data.result;
        // if( data && data.code == 0 ) {
        //     return data.result;
        // }else{
        //     Promise.reject(data);
        // }
    });
};

