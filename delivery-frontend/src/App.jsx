import { useEffect, useState } from 'react';
import './App.css';
import DashboardLinks from './DashboardLinks.jsx';

const API_BASE = import.meta.env.VITE_API_BASE_URL;
const API_ENV = import.meta.env.VITE_APP_ENV;

function App() {
    const [name, setName] = useState('');
    const [result, setResult] = useState(null);
    const [error, setError] = useState(null);
    const [categories, setCategories] = useState([]);

    const fetchCategories = async () => {
        try {
            const response = await fetch(`${API_BASE}/food-category`);
            const data = await response.json();
            setCategories(data.items || []);
        } catch (err) {
            console.error('Failed to fetch categories:', err);
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`${API_BASE}/food-category`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({displayName: name})
            });

            if (!response.ok) {
                const errorData = await response.json();
                setError(errorData);
                setResult(null);
                setName('');
                setTimeout(() => setError(null), 2500);
            } else {
                const data = await response.json();
                setResult(data);
                setError(null);
                setName('');
                fetchCategories();
            }
        } catch (err) {
            setError({message: 'Network error or server not reachable'});
            setResult(null);
        }
    };

    const handleDelete = async (id) => {
        try {
            await fetch(`${API_BASE}/food-category/${id}`, {
                method: 'DELETE'
            });
            fetchCategories();
        } catch (err) {
            console.error(`Failed to delete category with id ${id}:`, err);
        }
    };

    const handleDeleteAll = async () => {
        try {
            await fetch(`${API_BASE}/food-category`, {
                method: 'DELETE'
            });
            fetchCategories();
        } catch (err) {
            console.error('Failed to delete all categories:', err);
        }
    };

    return (
            <div className="app">
                {error?.message && (
                        <div className="error-banner">
                            ❌ {error.message}
                        </div>
                )}
                <div>
                    <div className="delete-div">
                        <h1>Food Categories App</h1>
                        <button onClick={handleDeleteAll}>
                            🗑️ Delete All
                        </button>
                    </div>
                    <DashboardLinks/>
                    <div className="formDiv">
                        <label>Create Category</label>
                        <form onSubmit={handleSubmit}>
                            <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                            />
                            <button type="submit">Create</button>
                        </form>
                    </div>
                    {result && (
                            <div className="result">
                                <h2>Success ✅</h2>
                                <pre>{JSON.stringify(result, null, 2)}</pre>
                            </div>
                    )}

                    {categories.length === 0 ? (
                            <p>No categories found.</p>
                    ) : (
                            <table>
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Display Name</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {categories.map((cat) => (
                                        <tr key={cat.foodCategoryId}>
                                            <td>{cat.foodCategoryId}</td>
                                            <td>{cat.displayName}</td>
                                            <td>
                                                <button onClick={() => handleDelete(cat.foodCategoryId)}>
                                                    ❌ Delete
                                                </button>
                                            </td>
                                        </tr>
                                ))}
                                </tbody>
                            </table>
                    )}
                </div>
            </div>
    )
            ;
}

export default App;
