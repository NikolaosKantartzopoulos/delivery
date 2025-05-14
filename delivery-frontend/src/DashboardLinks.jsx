import React from 'react';

const DashboardLinks = () => {
    const openUrl = (envVar) => {
        const url = import.meta.env[envVar];
        if (url) window.open(url, '_blank');
        else alert(`${envVar} is not defined`);
    };

    return (
            <div style={{marginBottom: '2rem'}}>
                <hr/>
                <div style={{display: 'flex', gap: '1rem', marginBottom: '1rem', marginTop: '1rem'}}>
                    <a href={import.meta.env.VITE_SPRINGBOOT_URL} target="_blank" rel="noopener noreferrer">
                        <button>🚀 Spring Boot</button>
                    </a>
                    <a href={import.meta.env.VITE_PROMETHEUS_URL} target="_blank" rel="noopener noreferrer">
                        <button>📊 Prometheus</button>
                    </a>
                    <a href={import.meta.env.VITE_GRAFANA_URL} target="_blank" rel="noopener noreferrer">
                        <button>📈 Grafana</button>
                    </a>
                </div>
                <hr/>
            </div>
    );
};

export default DashboardLinks;
