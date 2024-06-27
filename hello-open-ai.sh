echo "Hello OpenAI!"
API_KEY="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"
PROMPT="Tell me a Dad joke about computer"

curl https://api.openai.com/v1/chat/completions \
  -H "Authorization: Bearer $API_KEY" \
  -H "Content-Type: application/json" \
  -d '{"model":"gpt-4", "message": [{"role":"user", "content": "'"$PROMPT"'"}]'