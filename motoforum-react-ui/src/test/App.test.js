import { render, screen } from '@testing-library/react';
import App from '../components/App.jsx';

// Mock context provider
jest.mock('./YourContextProviderModule', () => ({
  __esModule: true,
  default: ({ children }) => children, // Mock provider simply renders its children
  YourContext: {
    Provider: ({ value, children }) => children, // Mock the context provider to pass value
  },
}));

test('renders main landing page', () => {
  render(<App />);
  // Find the Text
  const linkElement = screen.getByText(/ToDo Liste/i);
  expect(linkElement).toBeInTheDocument();
  expect(linkElement).toBeVisible();
  expect(linkElement).toHaveClass("headline")
});