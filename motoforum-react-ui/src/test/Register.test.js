import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Register } from '../components/Register.jsx'


test('renders register title', () => {
  render(<Register />);
  
  // Find the Text
  const linkElement = screen.getByText(/Register/i);
  expect(linkElement).toBeInTheDocument();
  expect(linkElement).toBeVisible();
  expect(linkElement).toHaveClass("headline")
});

test('renders input field', () => {
  render(<Register />);

  // Find the input field
  const inputElement = screen.getByRole('textbox');

  // Change the value of the inputField
  inputElement.value = "hello";

  // Check if value == "hello" + is in document
  expect(inputElement).toHaveValue("hello");
  expect(inputElement).toBeInTheDocument();
});