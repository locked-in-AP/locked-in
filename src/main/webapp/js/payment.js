// Contains only JavaScript for the payment section's expand/collapse

document.addEventListener('DOMContentLoaded', function() {
    const payOption = document.getElementById('credit-card-option');
    const cardDetails = document.getElementById('card-details');

    if (payOption && cardDetails) {
        payOption.addEventListener('click', function() {
            if (cardDetails.style.display === 'none' || cardDetails.style.display === '') {
                cardDetails.style.display = 'block';
            } else {
                cardDetails.style.display = 'none';
            }
        });
    } else {
        console.error("Payment elements not found. Make sure elements with IDs 'credit-card-option' and 'card-details' exist.");
    }
});