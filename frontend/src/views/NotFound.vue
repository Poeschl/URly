<template>
    <div class="level">
        <div class="level-item">
            <div class="card">
                <div class="card-content">
                    <div class="media">
                        <div class="media-content">
                            <p class="title is-4">The link you followed doesn't exist anymore. 😿</p>
                        </div>
                    </div>
                </div>
                <div class="card-image">
                    <figure class="image">
                        <img class="is-radiusless" src="https://http.cat/images/404.jpg" alt="Not Found">
                    </figure>
                </div>
                <div class="is-flex is-justify-content-center p-3">
                    <p>Presented by <a href="https://github.com/Poeschl/URly">URly</a> and <a href="https://http.cat">HTTP
                        Cats</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">

.card {
  .card-image {
    img {
      max-height: 72vh;
      object-fit: contain;
    }
  }
}

</style>
